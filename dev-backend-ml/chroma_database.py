from langchain_community.vectorstores import Chroma
import chromadb
from langchain_huggingface import HuggingFaceEmbeddings
import os


class ChromaDatabase:
    def __init__(self, port: int = 8000, collection_name: str = "recipes"):
        self.host = os.getenv("CHROMA_URL", default="localhost")
        self.port = port
        self.collection_name = collection_name
        self.chroma_client = chromadb.HttpClient(host=self.host, port=self.port)
        self.vector_db = Chroma(
            client=self.chroma_client,
            collection_name=self.collection_name,
            embedding_function=HuggingFaceEmbeddings(model_name="distiluse-base-multilingual-cased-v2")
        )

    def add_documents(self, documents: list):
        self.vector_db.add_documents(documents)

    def get_relevant_documents(self, query: str, k: int = 1):
        retriever = self.vector_db.as_retriever(search_type="mmr", search_kwargs={"k": k})
        docs_rel = retriever.invoke(query)
        return query, docs_rel
