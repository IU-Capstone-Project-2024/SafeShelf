from transformers import MarianMTModel, MarianTokenizer


class Translator:
    def __init__(self, model_name="Helsinki-NLP/opus-mt-ru-en"):
        self.translation_model = MarianMTModel.from_pretrained(model_name)
        self.tokenizer = MarianTokenizer.from_pretrained(model_name)
        self.translated_data = {}

    def translate_product_name(self, name):
        translated = self.translation_model.generate(**self.tokenizer(name, return_tensors="pt", padding=True))
        return self.tokenizer.decode(translated[0], skip_special_tokens=True)

    def process_and_translate(self, data):
        for product in data['productToGenerators']:
            translated = self.translate_product_name(product['name']).lower().rstrip().lstrip()
            print(product['name'], translated, product['userProductId'], end='\n\n')
            self.translated_data[translated] = product['userProductId']
            product['name'] = translated
            if 'userProductId' in product:
                del product['userProductId']
        print("Data processed and saved")
        return data
