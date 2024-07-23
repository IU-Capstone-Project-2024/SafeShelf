import React, {createContext, useContext, useState, ReactNode} from 'react';

// Define the context type
interface ResponseContextType {
    response: string;
    setResponse: React.Dispatch<React.SetStateAction<string>>;
}

// Create the context with default values
const ResponseContext = createContext<ResponseContextType | undefined>(undefined);

// Create a provider component
export const ResponseProvider: React.FC<{ children: ReactNode }> = ({children}) => {
    const [response, setResponse] = useState<string>('');

    return (
        <ResponseContext.Provider value={{response, setResponse}}>
            {children}
        </ResponseContext.Provider>
    );
};

// Create a custom hook for easy access to the context
export const useResponse = () => {
    const context = useContext(ResponseContext);
    if (!context) {
        throw new Error('useResponse must be used within a ResponseProvider');
    }
    return context;
};
