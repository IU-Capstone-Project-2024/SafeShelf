import re

recipe = """Recipe title: Hearty Pea Bean Potato Soup for One

Ingredients:
- Tomatoes: 103g (1/2 lb)
- Apples: 105g (1 medium)
- Noodles: 40g (1 small)

How to cook:

1. Cook the pea beans until mushy.
2. While the beans are cooking, dice the potatoes, carrots, and onions.
3. Add the diced vegetables to the cooked beans along with salt, pepper, and tomato soup.
4. Add a small amount of evaporated milk to thicken the soup. Cook the soup until the vegetables are cooked.
5. Serve the soup warm. Enjoy your tasty and nutritious meal!

This delicious soup is packed with protein and nutrients. The pea beans and potatoes provide a good source of fiber, while the onion adds flavor and aroma. The soup is also low in calories and fat, making it an excellent choice for a healthy meal. Give this recipe a try and let us know what you think.

Note:
- If you are unable to find pea beans, you can substitute them with a variety of beans such as black beans, kidney beans, or chickpeas.
- You can also add other vegetables to this recipe, such as bell peppers, zucchini, or corn.
- To make this recipe vegetarian, use vegetable broth instead of chicken broth.
- You can also add additional spices to the recipe, such as cumin, paprika, or curry powder.
- If you prefer a smoother soup, you can blend the soup using a blender or an immersion blender."""


def transform_recipe(translated_product, recipe_main=recipe):
    title_match = re.search(r"Recipe title:\s*(.*)", recipe_main)
    recipe_title = title_match.group(1) if title_match else ""

    ingredients_match = re.search(r"Ingredients:\s*((?:- .*?\n)+)", recipe_main)
    ingredients_text = ingredients_match.group(1) if ingredients_match else ""
    ingredients = []
    ingredient_lines = ingredients_text.split("\n")
    for line in ingredient_lines:
        if line:
            ingredient_match = re.match(r"- (.*):\s*(\d+)", line)
            if ingredient_match:
                name = ingredient_match.group(1)
                user_product_id = translated_product[name]
                weight = ingredient_match.group(2)
                ingredients.append({"id": user_product_id, "weight": int(weight)})

    description_match = re.search(r"How to cook:\s*(.*)", recipe_main, re.DOTALL)
    description = description_match.group(1).strip() if description_match else ""
    serialize_recipe = {
        'recipeTitle': recipe_title,
        'ingredients': ingredients,
        'description': description
    }
    return serialize_recipe
