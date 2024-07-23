import React, {createContext, useContext, useState, ReactNode} from 'react';

type DataRecord = {
    [fieldName: number]: string;
}

// Define the context type
interface ResponseContextType {
    datesDict: DataRecord;
    setDict: React.Dispatch<React.SetStateAction<DataRecord>>;
}

// Create the context with default values
const IndexContext = createContext<ResponseContextType | undefined>(undefined);

// Create a provider component
export const DateProvider: React.FC<{ children: ReactNode }> = ({children}) => {
    const [datesDict, setDict] = useState<DataRecord>({});

    return (
        <IndexContext.Provider value={{datesDict, setDict}}>
            {children}
        </IndexContext.Provider>
    );
};

// Create a custom hook for easy access to the context
export const useDate = () => {
    const context = useContext(IndexContext);
    if (!context) {
        throw new Error('useResponse must be used within a DateProvider');
    }
    return context;
};
