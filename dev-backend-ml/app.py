import os
from flask import Flask, request, jsonify
from llm_model import LLM
from exctracting_data import extract_data
from serialize_data import transform_recipe

app = Flask(__name__)
llm = LLM()
os.environ["TOKENIZERS_PARALLELISM"] = "false"


@app.route('/breakfast', methods=['POST'])
def generate_breakfast():
    data = request.json
    query, recipe, translated_products = extract_data(data, 0)
    instruction = llm.generate_response(query, recipe)
    print(instruction)
    dish = transform_recipe(translated_products, instruction)
    print(dish)
    return jsonify(dish)


@app.route('/lunch', methods=['POST'])
def generate_lunch():
    data = request.json
    query, recipe, translated_products = extract_data(data, 1)
    instruction = llm.generate_response(query, recipe)
    print(instruction)
    dish = transform_recipe(translated_products, instruction)
    print(dish)
    return jsonify(dish)


@app.route('/dinner', methods=['POST'])
def generate_dinner():
    data = request.json
    query, recipe, translated_products = extract_data(data, 2)
    instruction = llm.generate_response(query, recipe)
    print(instruction)
    dish = transform_recipe(translated_products, instruction)
    print(dish)
    return jsonify(dish)


if __name__ == '__main__':
    app.run(host='0.0.0.0', port=8001)
