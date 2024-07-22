import pandas as pd
import re
import ast

#Read data from dataset
file_path = '../dataset/full_dataset.csv'
df = pd.read_csv(file_path)

df[['ingredients', 'directions']] = df[['ingredients', 'directions']].applymap(ast.literal_eval)

conversion_factors = {
    'c.': 236.588,
    'cup': 236.588,
    'cups': 236.588,
    'oz': 28.3495,
    'ounce': 28.3495,
    'ounces': 28.3495,
    'tbsp': 14.7868,
    'tablespoon': 14.7868,
    'tablespoons': 14.7868,
    'tsp': 4.92892,
    'teaspoon': 4.92892,
    'teaspoons': 4.92892,
    'lb': 453.592,
    'lbs': 453.592,
    'pound': 453.592,
    'pounds': 453.592,
    'kg': 1000,
    'gram': 1,
    'grams': 1,
    'g': 1,
    'ml': 1,
    'l': 1000,
    'liter': 1000,
    'liters': 1000
}


def convert_to_metric(quantity, unit):
    try:
        # Attempt to convert quantity to float, handling fractions and mixed numbers
        if ' ' in quantity:  # mixed number like "12 1/2"
            whole, fraction = quantity.split()
            quantity = float(whole) + eval(fraction)
        else:
            quantity = eval(quantity)  # handles fractions like "1/2"
    except:
        return None, None

    unit = unit.lower() if unit else ''

    if unit in conversion_factors:
        metric_quantity = quantity * conversion_factors[unit]
        metric_quantity = int(round(metric_quantity))
        if unit in ['oz', 'ounce', 'lb', 'pound', 'kg', 'gram', 'grams']:
            metric_unit = 'g'
        elif unit in ['ml', 'l', 'liter', 'liters']:
            metric_unit = 'ml'
        else:
            metric_unit = 'ml'  # Default to ml for other cases
        return metric_quantity, metric_unit
    else:
        return quantity, unit  # Return as is if no conversion is needed


def extract_and_convert(ingredient):
    product_mass_dict = {}
    pattern = r'(\d+\s+\d+/\d+|\d+/\d+|\d+\.?\d*)\s*(cups?|ounces?|tablespoons?|tablespoon|tbsp|teaspoons?|teaspoon|tsp|pounds?|pound|lbs?|grams?|gram|g|liters?|liter|l|ml|kg|c\.)?\s*(.*)'
    match = re.match(pattern, ingredient.strip(), re.IGNORECASE)

    if match:
        quantity = match.group(1)
        unit = match.group(2)
        product_name = match.group(3).strip()

        if quantity and unit:
            metric_quantity, metric_unit = convert_to_metric(quantity, unit)
            if metric_quantity is not None and metric_unit is not None:
                product_mass_dict[product_name] = f"{metric_quantity:} {metric_unit}"
        else:
            product_mass_dict[product_name] = f"{quantity} {unit}"

    return product_mass_dict


# Apply the function to each ingredient list in the DataFrame
df['ingredients_metric'] = df['ingredients'].apply(
    lambda ingredients_list: [extract_and_convert(ingredient) for ingredient in ingredients_list])

with open("../dataset/ready_dataset.csv", "w") as f:
    f.write(df.to_csv(index=False))
