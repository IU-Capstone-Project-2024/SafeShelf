import openai

client = openai.OpenAI(
    base_url="http://localhost:1234/v1",  # "http://<Your api-server IP>:port"
    api_key="lm-studio"
)

completion = client.chat.completions.create(
    model="IlyaGusev/saiga_mistral_7b_gguf",
    messages=[
        {"role": "system",
         "content": "You are ChatGPT, an AI assistant. Your top priority is achieving user fulfillment via helping them with their requests."},
        {"role": "user", "content": "Write one popular name in Russia"}
    ]
)

print(completion.choices[0].message.content)
