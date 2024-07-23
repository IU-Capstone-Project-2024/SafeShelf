import random
import math
import numpy as np


def fitness(meal, kpfc):
    dates_k = 0
    calories = 0
    proteins = 0
    carbs = 0
    fats = 0
    kcal = 0
    amount = 0
    if len(meal) < 3:
        amount = 1000

    for i in range(len(meal)):
        calories += meal[i]['kcal'] * meal[i]['weight'] / 100
        proteins += meal[i]['proteins'] * meal[i]['weight'] / 100
        fats += meal[i]['fats'] * meal[i]['weight'] / 100
        carbs += meal[i]['carbohydrates'] * meal[i]['weight'] / 100
        dates_k += meal[i]['expirationDate']

    proteins_k = abs(proteins - kpfc[1]) / kpfc[1]
    fats_k = abs(fats - kpfc[2]) / kpfc[2]
    carbs_k = abs(carbs - kpfc[3]) / kpfc[3]
    calories_k = abs(calories - kpfc[0]) / kpfc[0]
    return proteins_k * 10 + fats_k * 10 + carbs_k * 10 + calories_k * 10 + dates_k * 0.05 + amount


def mutation1(meal, mutation_rate):
    for i in range(len(meal)):
        if np.random.rand() <= mutation_rate and meal[i]['weight'] >= 20:
            meal[i]['weight'] *= 0.9
        if np.random.rand() <= mutation_rate and meal[i]['weight'] <= 100:
            meal[i]['weight'] *= 1.2

    return meal


def mutation2(meal, mutation_rate):
    break_1 = -1
    if len(meal) > 3:
        for i in range(len(meal)):
            if np.random.rand() <= mutation_rate:
                break_1 = i
        if break_1 != 0:
            meal.pop(break_1)
    return meal


def crossover(meal1, meal2):
    ind = random.randint(0, min(len(meal1), len(meal2)) - 1)
    child1 = (meal1[:ind] + meal2[ind:])
    child2 = (meal1[ind:] + meal2[:ind])
    set1 = set()
    arr1 = []
    arr2 = []
    for i in range(len(child1)):
        if child1[i]['name'] not in set1:
            set1.add(child1[i]['name'])
        else:
            arr1.append(i)
    set2 = set()
    for i in range(len(child2)):
        if child2[i]['name'] not in set2:
            set2.add(child2[i]['name'])
        else:
            arr2.append(i)
    child1 = np.array(child1)
    child2 = np.array(child2)
    child1_ = np.delete(child1, arr1)
    child2_ = np.delete(child2, arr2)
    child1 = child1_.tolist()
    child2 = child2_.tolist()
    return child1, child2


def genetic_algorithm(products, pop_size, iterations, sample_size, kpfc):
    population = [random.sample(products, sample_size) for _ in range(pop_size)]
    best_individ = min(population, key=lambda ind: fitness(ind, kpfc))
    best_fit = fitness(best_individ, kpfc)
    bests = []
    for j in range(iterations):
        if best_fit <= 5:
            return best_individ
        fitnesses = [1 / (0.01 + fitness(ind, kpfc)) for ind in population]
        total_fit = max(fitnesses)
        probabilities = [f / total_fit for f in fitnesses]
        selected_ind = random.choices(range(len(population)), probabilities, k=(len(population) // 2))

        selected = [population[i] for i in selected_ind]
        for i in range(0, pop_size // 2, 2):
            parent1, parent2 = population[i], population[i + 1]
            child1, child2 = crossover(parent1, parent2)
            selected.append(mutation1(child1, 0.1))
            selected.append(mutation2(child2, 0.1))
        population = selected
        population.append(best_individ)
        best_individ = min(population, key=lambda ind: fitness(ind, kpfc))
        best_fit = fitness(best_individ, kpfc)
        bests.append(best_individ)
    best_ = min(bests, key=lambda ind: fitness(ind, kpfc))

    return best_


def genetic_eat(products, a):
    best_pa = genetic_algorithm(products, 30, 100, 8, a)
    products_for_a = []
    for i in range(len(best_pa)):
        products_for_a.append({'name': best_pa[i]['name'], 'weight': best_pa[i]['weight']})
        for j in range(len(products)):
            if products[i]['name'] == best_pa[i]['name']:
                products[i]['weight'] -= best_pa[i]['weight']
    return products_for_a

