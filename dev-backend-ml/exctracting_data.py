import json
from translator import Translator
from chroma_database import ChromaDatabase
from genetic_dishes import genetic_eat

translator = Translator()
vector_db = ChromaDatabase()
file_path = '/Users/emildavlityarov/PycharmProjects/SafeShelf/recipes.json'
with open(file_path, 'r') as file:
    recipes = json.load(file)


def extract_data(data, type_dish):
    updated_data = translator.process_and_translate(data)
    kpfc = updated_data['kpfc']
    products = updated_data['productToGenerators']
    breakfast = genetic_eat(products, kpfc[type_dish])
    query = ""
    for product in breakfast:
        query += product['name'] + ": " + str(int(product['weight'])) + 'g, '
    query1, doc_rel = vector_db.get_relevant_documents(query)
    recipe_title = doc_rel[0].metadata['recipe_title']
    return query1, recipes[recipe_title], translator.translated_data
