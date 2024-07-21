from transformers import MarianMTModel, MarianTokenizer

class Translator:
    def __init__(self, model_name="Helsinki-NLP/opus-mt-ru-en"):
        # Initialize the translation model and tokenizer once
        self.translation_model = MarianMTModel.from_pretrained(model_name)
        self.tokenizer = MarianTokenizer.from_pretrained(model_name)

    def translate_product_name(self, name):
        # Translate the product name to English
        translated = self.translation_model.generate(**self.tokenizer(name, return_tensors="pt", padding=True))
        return self.tokenizer.decode(translated[0], skip_special_tokens=True)

    def process_and_translate(self, data, output_file='processed_data.json'):
        # Process and translate product names
        for product in data['productToGenerators']:
            # Translate the product name to English
            product['name'] = self.translate_product_name(product['name'])

        print("Data processed and saved")
        return data
