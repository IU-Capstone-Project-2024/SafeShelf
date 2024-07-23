import React from 'react';
import ReactDOM from 'react-dom/client';
import './styles/index.css';
import {BrowserRouter} from "react-router-dom";
import App from './App';
import {ResponseProvider} from "./pages/ResponseContext";
import {IndexProvider} from "./pages/IndicesHook";
import {DateProvider} from "./pages/DatesHook";
import {WeightProvider} from "./pages/WeightHook";

const root = ReactDOM.createRoot(
    document.getElementById('root') as HTMLElement
);

root.render(
    <React.StrictMode>
        <WeightProvider>
            <DateProvider>
                <IndexProvider>
                    <ResponseProvider>
                        <BrowserRouter>
                            <App/>
                        </BrowserRouter>
                    </ResponseProvider>
                </IndexProvider>
            </DateProvider>
        </WeightProvider>
    </React.StrictMode>
);