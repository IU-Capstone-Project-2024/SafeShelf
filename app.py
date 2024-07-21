import json

from flask import Flask, request
from translator import Translator
from chroma_database import ChromaDatabase
from genetic_dishes import genetic_eat
from llm_model import LLM
import os

app = Flask(__name__)

translator = Translator()
vector_db = ChromaDatabase()
llm = LLM()
os.environ["TOKENIZERS_PARALLELISM"] = "false"
file_path = '/Users/emildavlityarov/PycharmProjects/backend_ml/recipes.json'
with open(file_path, 'r') as file:
    recipes = json.load(file)


@app.route('/breakfast', methods=['POST'])
def generate_breakfast():
    data = request.json
    updated_data = translator.process_and_translate(data)
    kpfc = updated_data['kpfc']
    products = updated_data['productToGenerators']
    breakfast = genetic_eat(products, kpfc[0])
    query1 = ""
    for product in breakfast:
        query1 += product['name'] + ": " + str(int(product['weight'])) + 'g, '
    query1, doc_rel = vector_db.get_relevant_documents(query1)
    recipe_title = doc_rel[0].metadata['recipe_title']
    recipe = llm.generate_response(query1, recipes[recipe_title])

    print(recipe)
    return "", 200


@app.route('/lunch', methods=['POST'])
def generate_lunch():
    data = request.json
    updated_data = translator.process_and_translate(data)
    kpfc = updated_data['kpfc']
    products = updated_data['productToGenerators']
    eat = genetic_eat(products, kpfc[1])
    print(eat)
    query1 = ""
    for product in eat:
        query1 += product['name'] + ": " + str(int(product['weight'])) + 'g, '
    query1, doc_rel = vector_db.get_relevant_documents(query1)
    recipe_title = doc_rel[0].metadata['recipe_title']
    recipe = llm.generate_response(query1, recipes[recipe_title])

    print(recipe)
    return "", 200


@app.route('/dinner', methods=['POST'])
def generate_dinner():
    data = request.json

    return "", 200


if __name__ == '__main__':
    app.run(host='0.0.0.0', port=8001)
