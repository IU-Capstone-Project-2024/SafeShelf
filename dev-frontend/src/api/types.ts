import React from "react";

export interface Diet {
    dishes: {
        "id": string,
        "name": string,
        "ingredients": Array<IngredientsEntity>,
        "description": string,
        "type": string
    }[];
}

export interface IngredientsEntity {
    userProductId: Number,
    name: string,
    weight: Number
}

export interface ComponentDiet {
    currentState: string,
    dishes: {
        "id": string,
        "name": string,
        "ingredients": Array<IngredientsEntity>,
        "description": string,
        "type": string
    }[];
}

export interface sendCooked {
    login: string | null,
    id: string
}

export interface Grocery {
    id: number;
    name: string;
    weight: number;
    kkal: number;
    proteins: number;
    fats: number;
    carbohydrates: number;
}

export interface newGroceries {
    login: string,
    products: {
        id: number;
        name: string;
        weight: number;
        kcal: number;
        proteins: number;
        fats: number;
        carbohydrates: number;
        date: string;
    }[];
}

export interface CardContainerProps {
    cards: {
        groceryName: string,
        parameters: Array<string>,
        description: string,
        date: string
    }[];
}


export interface GroceryContainerProps {
    groceries: {
        id: number;
        name: string;
        weight: number;
        kcal: number;
        proteins: number;
        fats: number;
        carbohydrates: number;
        date: string;
    }[];
}

export interface sendingQR {
    login: string | null | undefined;
    metaStringProducts: string | null | undefined;
}

export interface updating {
    login: string,
    productID: number,
    date: string
}

export interface deleting {
    login: string,
    userProductID: number,
}

export interface LoginData {
    login: string,
    password: string
}

export interface statusResponse400 {
    code: string,
    description: string,
    exceptionName: string,
    exceptionMessage: string
}

export interface LoginPageProps {
    onChange: (value: boolean) => void;
    setResponse: React.Dispatch<React.SetStateAction<string>>;
}

export interface ProfileData {
    login: string;
    name: string;
    surname: string;
    height: number;
    weight: number;
    age: number;
    sex: string;
    lifestyle: string;
    goal: string;
}

export interface UpdatingData {
    oldLogin: string;
    login: string;
    name: string;
    surname: string;
    height: number;
    weight: number;
    password: string;
    age: number;
    sex: string;
    lifestyle: string;
    goal: string;
}

export interface signData {
    login: string;
    password: string;
    name: string;
    surname: string;
    height: number;
    weight: number;
    age: number;
    sex: string;
    lifestyle: string;
    goal: string;
}
