import React, {createContext, useContext, useState, ReactNode} from 'react';

// Define the context type
interface ResponseContextType {
    indicesArray: number[];
    setArray: React.Dispatch<React.SetStateAction<number[]>>;
}

// Create the context with default values
const IndexContext = createContext<ResponseContextType | undefined>(undefined);

// Create a provider component
export const IndexProvider: React.FC<{ children: ReactNode }> = ({children}) => {
    const [indicesArray, setArray] = useState<number[]>([]);

    return (
        <IndexContext.Provider value={{indicesArray, setArray}}>
            {children}
        </IndexContext.Provider>
    );
};

// Create a custom hook for easy access to the context
export const useIndex = () => {
    const context = useContext(IndexContext);
    if (!context) {
        throw new Error('useResponse must be used within a useIndexProvider');
    }
    return context;
};
