import openai


class LLM:
    def __init__(self):
        self.client = openai.OpenAI(
            base_url="http://localhost:9090/v1",
            api_key="sk-no-key-required"
        )
        self.model = "gpt-3.5-turbo"
        self.role_consumer = "system"
        self.role_producer = "user"

    def generate_response(self, query, docs_rel):
        request = (
            f'Write a recipe for a dish for one person, using only all these products with weights'
            f': {query}. Use the information from the recipe book excerpt and adapt it for our products '
            f': {docs_rel}. Write it in format: Recipe title: ...\nIngredients:\n - ingredient: weight\n ..., How to cook:\n ...')
        print(request, end='\n\n\n\n\n\n')
        completion = self.client.chat.completions.create(
            model="gpt-3.5-turbo",
            messages=[
                {"role": "system",
                 "content": "You are a cook helper. Your top priority is achieving good recipes from given products "
                            "You will receive products and help to cook."},
                {"role": "user", "content": request}
            ]
        )
        return completion.choices[0].message.content
