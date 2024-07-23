import {FC, useEffect, useState} from "react";
import HeaderPage from "./HeaderPage";
import '../styles/profileStyles.css'
import {ProfileData, statusResponse400, UpdatingData} from "../interfaces/sign-login-interface";

const Profile: FC<{ setMenuVisible: (visible: boolean) => void }> = ({setMenuVisible}) => {
    // In response we save mail info about user
    // const [profileData, setProfileData] = useState<ProfileData | null>(null);
    const [currentMail, setCurrentMail] = useState<string>();
    const [currentPass, setCurrentPass] = useState<string>();
    const [currentName, setCurrentName] = useState<string>();
    const [currentSurname, setCurrentSurname] = useState<string>();
    const [currentHeight, setCurrentHeight] = useState<number>();
    const [currentWeight, setCurrentWeight] = useState<number>();
    const [currentAge, setCurrentAge] = useState<number>();
    const [currentSex, setCurrentSex] = useState<string>();
    const [currentLifestyle, setCurrentLifestyle] = useState<string>();
    const [currentGoal, setCurrentGoal] = useState<string>();

    const [isEmailValid, setIsEmailValid] = useState(true);
    const [isPwValid, setIsPwValid] = useState(true);
    const [isNameValid, setIsNameValid] = useState(true);
    const [isSurnameValid, setIsSurnameValid] = useState(true);
    const [isAgeValid, setIsAgeValid] = useState(true);
    const [isHeightValid, setIsHeightValid] = useState(true);
    const [isWeightValid, setIsWeightValid] = useState(true);

    const [disabled, setDisabled] = useState(true);

    const validateSpaces = (str: string): boolean => {
        const spaceRegex = /^\S*$/;
        return spaceRegex.test(str);
    };

    const validateAlph = (str: string): boolean => {
        const alRegex = /^[A-Za-z]+$/;
        return alRegex.test(str);
    };

    const lifeChange = (event: React.ChangeEvent<HTMLSelectElement>) => {
        const value = event.target.value;
        setCurrentLifestyle(value);
    };

    const sexChange = (event: React.ChangeEvent<HTMLSelectElement>) => {
        const value = event.target.value;
        setCurrentSex(value);
    };

    const goalChange = (event: React.ChangeEvent<HTMLSelectElement>) => {
        const value = event.target.value;
        setCurrentGoal(value);
    };

    useEffect(() => {
        const fetchProfileData = async () => {
            try {
                let userLogin = sessionStorage.getItem('userLogin')
                if (!userLogin) {
                    userLogin = "";
                }
                const currResponse = await fetch(`/account/${userLogin.toString()}`);
                const data: ProfileData = await currResponse.json();
                // setProfileData(data);
                setCurrentMail(data.login)
                setCurrentAge(Number(data.age));
                setCurrentHeight(Number(data.height));
                setCurrentWeight(Number(data.weight));
                setCurrentPass("");
                setCurrentSex(data.sex);
                setCurrentSurname(data.surname);
                setCurrentName(data.name);
                setCurrentLifestyle(data.lifestyle);
                setCurrentGoal(data.goal);
            } catch (err) {
                alert(err);
            }
        };
        fetchProfileData().then();
    }, []);

    // For now is useless
    const handleSave = async () => {
        let oldLogin = sessionStorage.getItem('userLogin')

        if (!oldLogin) {
            oldLogin = "";
        }

        const login = (document.getElementById('mail') as HTMLInputElement).value;
        let password = (document.getElementById('new-password') as HTMLInputElement).value;
        const name = (document.getElementById('name') as HTMLInputElement).value;
        const surname = (document.getElementById('surname') as HTMLInputElement).value;
        const height = Number((document.getElementById('height') as HTMLInputElement).value);
        const weight = Number((document.getElementById('weight') as HTMLInputElement).value);
        const age = Number((document.getElementById('age') as HTMLInputElement).value);
        const sex = (document.getElementById('gender') as HTMLSelectElement).value;
        const lifestyle = (document.getElementById('lifestyle') as HTMLSelectElement).value;
        const goal = (document.getElementById('goal') as HTMLSelectElement).value;

        if (!password) {
            password = "";
        }

        const updatedProfile: UpdatingData = {
            oldLogin,
            login,
            name,
            surname,
            height,
            weight,
            password,
            age,
            sex,
            lifestyle,
            goal
        }

        try {
            if (!validateSpaces(login) || !validateAlph(login)) {
                setIsEmailValid(false);
                const emailInput = document.getElementById('mail') as HTMLInputElement;
                emailInput.value = "Input should contain only english alphabet";
                throw new Error("ERROR WHILE INPUT LOGIN");
            }

            if (!validateSpaces(password) && !password) {
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
            const currResponse = await fetch("/account", {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(updatedProfile),
            });

            const currentStatus = currResponse.status;

            if (currentStatus === 200) {
                const data = await currResponse.json();
                setCurrentMail(data.login)
                setCurrentAge(Number(data.age));
                setCurrentPass(data.password);
                setCurrentSex(data.sex);
                setCurrentHeight(Number(data.height));
                setCurrentWeight(Number(data.weight));
                setCurrentSurname(data.surname);
                setCurrentName(data.name)
                setCurrentLifestyle(data.lifestyle)
                setCurrentGoal(data.goal)
                sessionStorage.setItem('userLogin', data.login);
                setDisabled(true);
            } else if (currentStatus >= 400) {
                /* Значит какой-то кринж и мы этот кринж обрабатываем */
                const badRequest: statusResponse400 = await currResponse.json();
                const stringError: string = badRequest.description;
                throw new Error(stringError);
            } else {
                // ЧТО ДЕЛАЕМ?
                throw new Error('Непредвиденная ошибка. Попробуйте позже');
            }

        } catch (err) {
            console.warn(err);
        }
    }


    const editSave = () => {
        setDisabled(false);
    }

    return (
        <>
            <HeaderPage setMenuVisible={setMenuVisible}/>
            <div className='pageContainer'>
                <h1>Profile</h1>
                <div className="inputs">
                    <label htmlFor="mail">Login</label>
                    <input type="email" id="mail" name="login" defaultValue={currentMail} disabled={disabled} style={{
                        border: isEmailValid ? 'none' : '2px solid red'
                    }} required/>

                    <label htmlFor="new-password">Password</label>
                    <input type="new-password" id="new-password" defaultValue={currentPass} disabled={disabled} style={{
                        border: isPwValid ? 'none' : '2px solid red'
                    }}
                           required/>

                    <label htmlFor="name">Name:</label>
                    <input type="text" id="name" name="name" defaultValue={currentName} disabled={disabled} style={{
                        border: isNameValid ? 'none' : '2px solid red'
                    }} required/>

                    <label htmlFor="surname">Surname:</label>
                    <input type="text" id="surname" name="surname" defaultValue={currentSurname} disabled={disabled}
                           style={{
                               border: isSurnameValid ? 'none' : '2px solid red'
                           }}
                           required/>

                    <label htmlFor="height">Height:</label>
                    <input type="number" id="height" name="height" defaultValue={currentHeight} disabled={disabled}
                           style={{
                               border: isHeightValid ? 'none' : '2px solid red'
                           }}
                           required/>

                    <label htmlFor="weight">Weight:</label>
                    <input type="number" id="weight" name="weight" defaultValue={currentWeight} disabled={disabled}
                           style={{
                               border: isWeightValid ? 'none' : '2px solid red'
                           }}
                           required/>

                    <label htmlFor="age">Age:</label>
                    <input type="number" id="age" name="age" defaultValue={currentAge} style={{
                        border: isAgeValid ? 'none' : '2px solid red'
                    }}
                           disabled={disabled} required/>

                    <label htmlFor="gender">Gender:</label>
                    <select id="gender" name="gender" value={currentSex} disabled={disabled} onChange={sexChange}
                            required>
                        <option value="M">Male</option>
                        <option value="F">Female</option>
                    </select>

                    <label htmlFor="lifestyle">Lifestyle:</label>
                    <select id="lifestyle" name="lifestyle" value={currentLifestyle} disabled={disabled}
                            onChange={lifeChange} required>
                        <option value="Office worker">Office worker</option>
                        <option value="Sedentary work, light fitness">Sedentary work, light fitness</option>
                        <option value="Sedentary work, intense sports">Sedentary work, intense sports</option>
                        <option value="Work on feet (without lifting heavy weights)">Work on feet (without lifting heavy
                            weights)
                        </option>
                        <option value="Work on feet (without lifting heavy weights), 3 times a week intense sport">Work
                            on
                            feet (without lifting heavy weights), 3 times a week intense sport
                        </option>
                        <option value="Daily trainings\Work associated with physical activity">Daily trainings\Work
                            associated with physical activity
                        </option>
                    </select>

                    <label htmlFor="goal">Goal:</label>
                    <select id="goal" name="goal" value={currentGoal} disabled={disabled} onChange={goalChange}
                            required>
                        <option value="loss">Lose weight</option>
                        <option value="get">Get weight</option>
                        <option value="stay">Stay</option>
                    </select>
                </div>
                <div className="buttons">
                    <button className='fixed-buttons edit' onClick={editSave}>Edit</button>
                    <button className='fixed-buttons save' onClick={handleSave}>Save</button>
                </div>
            </div>

        </>
    )
};

export default Profile;