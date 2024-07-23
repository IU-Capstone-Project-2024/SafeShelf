export interface DietInterface {
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

export interface sendCooked {
    login: string | null,
    id: string
}

export interface NewDiet {
    currentState: string,
    dishes: {
        "id": string,
        "name": string,
        "ingredients": Array<IngredientsEntity>,
        "description": string,
        "type": string
    }[];
}