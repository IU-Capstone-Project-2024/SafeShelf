import React, {FC} from "react";
import '../styles/login.css'
import {useNavigate} from "react-router-dom";
import {LoginData, LoginPageProps, statusResponse400} from "../interfaces/sign-login-interface";


const LoginPage: FC<LoginPageProps> = ({onChange, setResponse}) => {
    /* По нажатию будет срабатывать кнопка, которая собирает всю инфу с полей и проверяет ее в handle change, отправляя
    либо пустоту, либо айдишник для послед запросов на бэк*/
    const handleChange = async () => {
        const login = (document.getElementById('mail') as HTMLInputElement).value;
        const password = (document.getElementById('password') as HTMLInputElement).value;

        const loginData: LoginData = {
            login,
            password
        }

        try {
            if (login === '' || password === '') {
                throw new Error('Incorrect input: fill all fields!');
            }
            const sending = await fetch('/account/login', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(loginData),
            });

            const currentStatus: number = sending.status;

            if (currentStatus === 200) {
                /* Все nice и мы ставим в качестве внутреннего ID логин юзера
                * Нужен ли тут вообще какой-то JSON или нет? Как по мне нет, и так все супер */
                setResponse(login);
                sessionStorage.setItem('userLogin', login.toString());
                onChange(true)
            } else if (currentStatus >= 400) {
                setResponse(login);
                sessionStorage.setItem('userLogin', login);
                /* Значит какой-то кринж и мы этот кринж обрабатываем */
                const badRequest: statusResponse400 = await sending.json();
                const stringError: string = badRequest.description;
                throw new Error(stringError);
            } else {
                // ЧТО ДЕЛАЕМ?
                throw new Error('Непредвиденная ошибка. Попробуйте позже');
            }
        } catch (error) {
            alert(error)
        }

    }

    const navigate = useNavigate();

    const goToSignPage = () => {
        navigate('/sign');
    };

    return (
        <div className="wrapper">
            <h1>SafeShelf</h1>
            <div className="inputs-log">
                <label htmlFor="mail">Login</label>
                <input type="email" id="mail" name="login" required/>

                <label htmlFor="pass">Password</label>
                <input type="password" id="password" required/>

                <button onClick={handleChange} className='login-btn'>Log in</button>
            </div>
            <div className='text'>Have no account?</div>
            <button className='sign-btn' onClick={goToSignPage}>Sign up</button>
        </div>
    )
}

export default LoginPage;