import {useNavigate} from "react-router-dom";
import '../styles/signer.css'
import React, {useState} from "react";
import {signData, statusResponse400} from "../interfaces/sign-login-interface";

const SignPage = () => {
    const navigate = useNavigate();
    const [isEmailValid, setIsEmailValid] = useState(true);
    const [isPwValid, setIsPwValid] = useState(true);
    const [isNameValid, setIsNameValid] = useState(true);
    const [isSurnameValid, setIsSurnameValid] = useState(true);
    const [isAgeValid, setIsAgeValid] = useState(true);
    const [isHeightValid, setIsHeightValid] = useState(true);
    const [isWeightValid, setIsWeightValid] = useState(true);

    const validateSpaces = (str: string): boolean => {
        const spaceRegex = /^\S*$/;
        return spaceRegex.test(str);
    };

    const validateAlph = (str: string): boolean => {
        const alRegex = /^[A-Za-z]+$/;
        return alRegex.test(str);
    };

    const handleSignIn = async () => {
        const login = (document.getElementById('email') as HTMLInputElement).value;
        const password = (document.getElementById('new-password') as HTMLInputElement).value;
        const name = (document.getElementById('name') as HTMLInputElement).value;
        const surname = (document.getElementById('surname') as HTMLInputElement).value;
        const age = Number((document.getElementById('age') as HTMLInputElement).value);
        const sex = (document.getElementById('gender') as HTMLSelectElement).value;
        const height = Number((document.getElementById('height') as HTMLInputElement).value);
        const weight = Number((document.getElementById('weight') as HTMLInputElement).value);
        const lifestyle = (document.getElementById('lifestyle') as HTMLSelectElement).value;
        const goal = (document.getElementById('goal') as HTMLSelectElement).value;

        const signed: signData = {
            login,
            password,
            name,
            surname,
            height,
            weight,
            age,
            sex,
            lifestyle,
            goal
        }

        try {
            if (!login || !password || !name || !surname || !age || !height || !weight) {
                throw new Error("Empty data!");
            }

            if (!validateSpaces(login) || !validateAlph(login)) {
                setIsEmailValid(false);
                const emailInput = document.getElementById('email') as HTMLInputElement;
                emailInput.value = "Input should contain only english alphabet";
                throw new Error("ERROR WHILE INPUT LOGIN");
            }

            if (!validateSpaces(password)) {
                setIsEmailValid(true);
                setIsAgeValid(true);
                setIsHeightValid(true);
                setIsSurnameValid(true);
                setIsWeightValid(true);
                setIsPwValid(true)
                setIsNameValid(true);

                setIsPwValid(false);
                throw new Error("ERROR WHILE INPUT PASSWORD");
            }

            if (!validateSpaces(name) || name.length >= 30 || !validateAlph(name)) {
                setIsEmailValid(true);
                setIsAgeValid(true);
                setIsHeightValid(true);
                setIsSurnameValid(true);
                setIsWeightValid(true);
                setIsPwValid(true)

                setIsNameValid(false);
                const nameInput = document.getElementById('name') as HTMLInputElement;
                if (name.length > 30) {
                    nameInput.value = "Name should be less than 30 symbols";
                } else {
                    nameInput.value = "Input should contain only english alphabet";
                }
                throw new Error("ERROR WHILE INPUT NAME");
            }

            if (!validateSpaces(surname) || surname.length >= 30 || !validateAlph(surname)) {
                setIsEmailValid(true);
                setIsAgeValid(true);
                setIsHeightValid(true);
                setIsWeightValid(true);
                setIsPwValid(true)
                setIsNameValid(true);

                setIsSurnameValid(false);
                const nameInput = document.getElementById('surname') as HTMLInputElement;
                if (surname.length > 30) {
                    nameInput.value = "Name should be less than 30 symbols";
                } else {
                    nameInput.value = "Input should contain only english alphabet";
                }
                throw new Error("ERROR WHILE INPUT SURNAME");
            }

            if (weight < 1 || weight > 310) {
                setIsEmailValid(true);
                setIsAgeValid(true);
                setIsHeightValid(true);
                setIsSurnameValid(true);
                setIsPwValid(true)
                setIsNameValid(true);

                setIsWeightValid(false);
                const weightInput = document.getElementById('weight') as HTMLInputElement;
                weightInput.value = '';
                throw new Error("ERROR WHILE INPUT WEIGHT");
            }

            if (height < 1 || height > 220) {
                setIsEmailValid(true);
                setIsAgeValid(true);
                setIsSurnameValid(true);
                setIsWeightValid(true);
                setIsPwValid(true)
                setIsNameValid(true);

                setIsHeightValid(false);
                const heightInput = document.getElementById('height') as HTMLInputElement;
                heightInput.value = '';
                throw new Error("ERROR WHILE INPUT HEIGHT");
            }

            if (age < 1 || age > 110) {
                setIsEmailValid(true);
                setIsHeightValid(true);
                setIsSurnameValid(true);
                setIsWeightValid(true);
                setIsPwValid(true)
                setIsNameValid(true);

                setIsAgeValid(false);
                const ageInput = document.getElementById('age') as HTMLInputElement;
                ageInput.value = '';
                throw new Error("ERROR WHILE INPUT AGE");
            }

            const sending = await fetch("/account/register", {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(signed),
            });

            const currentStatus = sending.status;
            if (currentStatus === 200) {
                navigate('/');
            } else if (currentStatus >= 400) {
                /* Значит какой-то кринж и мы этот кринж обрабатываем */
                const badRequest: statusResponse400 = await sending.json();
                const stringError: string = badRequest.description;
                throw new Error(stringError);
            } else {
                // ЧТО ДЕЛАЕМ?
                throw new Error('Непредвиденная ошибка. Попробуйте позже');
            }
        } catch (error) {
            console.warn(error)
        }
    };

    return (
        <div className="wrapper">
            <h1>SafeShelf</h1>
            <div className="inputs-sig">

                <label htmlFor="mail">Login</label>
                <input type="email" id="email" name="login" style={{
                    border: isEmailValid ? 'none' : '2px solid red'
                }} required/>

                <label htmlFor="pass">Password</label>
                <input type="password" id="new-password" style={{
                    border: isPwValid ? 'none' : '2px solid red'
                }} required/>

                <label htmlFor="name">Name:</label>
                <input type="text" id="name" name="name" style={{
                    border: isNameValid ? 'none' : '2px solid red'
                }} required/>

                <label htmlFor="surname">Surname:</label>
                <input type="text" id="surname" name="surname" style={{
                    border: isSurnameValid ? 'none' : '2px solid red'
                }} required/>

                <label htmlFor="height">Height:</label>
                <input type="number" id="height" name="height" min="0" max="100" style={{
                    border: isHeightValid ? 'none' : '2px solid red'
                }} required/>

                <label htmlFor="weight">Weight:</label>
                <input type="number" id="weight" name="weight" min="0" max="100" style={{
                    border: isWeightValid ? 'none' : '2px solid red'
                }} required/>

                <label htmlFor="age">Age:</label>
                <input type="number" id="age" name="age" min="0" max="100" style={{
                    border: isAgeValid ? 'none' : '2px solid red'
                }} required/>

                <label htmlFor="gender">Gender:</label>
                <select id="gender" name="gender" required>
                    <option value="M">Male</option>
                    <option value="F">Female</option>
                </select>
                <label htmlFor="lifestyle">Lifestyle:</label>
                <select id="lifestyle" name="lifestyle" required>
                    <option value="Office worker">Office worker</option>
                    <option value="Sedentary work, light fitness">Sedentary work, light fitness</option>
                    <option value="Sedentary work, intense sports">Sedentary work, intense sports</option>
                    <option value="Work on feet (without lifting heavy weights)">Work on feet (without lifting heavy
                        weights)
                    </option>
                    <option value="Work on feet (without lifting heavy weights), 3 times a week intense sport">Work on
                        feet (without lifting heavy weights), 3 times a week intense sport
                    </option>
                    <option value="Daily trainings\Work associated with physical activity">Daily trainings\Work
                        associated with physical activity
                    </option>
                </select>

                <label htmlFor="goal">Goal:</label>
                <select id="goal" name="goal" required>
                    <option value="loss">Lose weight</option>
                    <option value="get">Get weight</option>
                    <option value="stay">Stay</option>
                </select>
                <button onClick={handleSignIn} className='signbtn'>Sign up</button>
            </div>
        </div>
    );
}

export default SignPage;