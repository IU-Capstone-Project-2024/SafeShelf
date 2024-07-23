import openai


class LLM:
    def __init__(self):
        self.client = openai.OpenAI(
            base_url="http://localhost:1234/v1",
            api_key="lm-studio",
        )
        self.model = "IlyaGusev/saiga_mistral_7b_gguf"

    def generate_response(self, query, docs_rel):
        request = (
            f'Write an one recipe for a dish for one person, using only these products with weights'
            f': {query}. Please use only the products that are written in the previous sentence. Use the information '
            f'from the recipe book but use the products from previous sentence.'
            f': {docs_rel}. Write it in format: Recipe title: ...\n Ingredients:\n - ingredient name: weight'
            f' \n How to cook:\n ... Please strictly follow this format and use only products that are '
            f'written in first sentence.')
        print(request)
        completion = self.client.chat.completions.create(
            model="gpt-3.5-turbo",
            messages=[
                {"role": "assistant",
                 "content": "You are a cook helper. Please strictly follow this format and use only products that are "
                            "providing in first sentence from user. Your top priority is achieving good recipes from "
                            "given products. You will receive products and help to cook."},
                {"role": "user", "content": request}
            ]
        )
        return completion.choices[0].message.content
