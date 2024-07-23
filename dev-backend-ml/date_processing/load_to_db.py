import json

import pandas as pd
from langchain.docstore.document import Document
from chroma_database import ChromaDatabase

# Read data from dataset
file_path = '../dataset/ready_dataset.csv'
df = pd.read_csv(file_path)
df = df.head(40000)


def load_processed_data(vector_db=ChromaDatabase()):
    documents_with_metadata = []

    recipes = {}
    for _, row in df.iterrows():
        recipes[row['title']] = {
            'directions': row['directions'],
            'ingredients': row['ingredients'],
            'NER': row['NER']
        }

    recipes_json = json.dumps(recipes, ensure_ascii=False, indent=4)

    with open('../recipes.json', 'w', encoding='utf-8') as file:
        file.write(recipes_json)

    with open('../recipes.json', 'r', encoding='utf-8') as file:
        recipes = json.load(file)

    for title, data in recipes.items():
        content = data['NER']
        doc = Document(content)
        doc.metadata['recipe_title'] = title
        documents_with_metadata.append(doc)

    # Add documents to vector database
    vector_db.add_documents(documents_with_metadata)

    # def split_list(input_list, chunk_size):
    #     for i in range(0, len(input_list), chunk_size):
    #         yield input_list[i:i + chunk_size]
    #
    # split_docs_chunked = split_list(documents_with_metadata, 41000)
    # for temp_list in split_docs_chunked:
    #     vector_db.add_documents(temp_list)


load_processed_data()
