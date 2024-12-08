import requests
import json

class LLM:
    def __init__(self):
        self.url = "http://0.0.0.0:8001/generate"

    def generate_response(self, query, docs_rel):
        prompt = (
            f'Write an one recipe for a dish for one person, using only these products with weights'
            f': {query}. Please use only the products that are written in the previous sentence. Use the information '
            f'from the recipe book but use the products from previous sentence.'
            f': {docs_rel}. Write it in format: Recipe title: ...\n Ingredients:\n - ingredient name: weight'
            f' \n How to cook:\n ... Please strictly follow this format and use only products that are '
            f'written in first sentence.')
        print(prompt)
        data = {
          "prompt": prompt,
          "temperature": 0.3,
          "top_k":35,  
          "top_p": 0.9,
          "max_tokens": 2048
        }
        headers = {"Content-Type": "application/json"}
        response = requests.post(self.url, data=json.dumps(data), headers = headers)

        return response.json()["text"][0]
