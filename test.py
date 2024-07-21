import openai

client = openai.OpenAI(
    base_url="http://localhost:9090/v1",
    api_key="sk-no-key-required"
)

completion = client.chat.completions.create(
    model="gpt-3.5-turbo",
    messages=[
        {"role": "system",
         "content": "You are ChatGPT, an AI assistant. Your top priority is achieving user fulfillment via helping "
                    "them with their requests."},
        {"role": "user", "content": "Write a limerick about python exceptions"}
    ]
)

print(completion.choices[0].message)
