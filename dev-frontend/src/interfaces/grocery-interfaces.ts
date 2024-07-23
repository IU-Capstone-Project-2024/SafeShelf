export interface GroceryList {
    allGroceries: Array<GroceryInterface>
}

export interface GroceryInterface {
    id: number;
    name: string;
    weight: number;
    kkal: number;
    proteins: number;
    fats: number;
    carbohydrates: number;
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

export interface updating {
    login: string,
    productID: number,
    date: string
}

export interface deleting {
    login: string,
    userProductID: number,
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


export interface sendingQR {
    login: string | null | undefined;
    metaStringProducts: string | null | undefined;
}