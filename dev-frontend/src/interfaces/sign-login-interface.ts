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