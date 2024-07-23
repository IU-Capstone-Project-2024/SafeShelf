import React, {createContext, useContext, useState, ReactNode} from 'react';

type WeightRecord = {
    [fieldName: number]: string;
}

// Define the context type
interface ResponseContextType {
    weightDict: WeightRecord;
    setWeight: React.Dispatch<React.SetStateAction<WeightRecord>>;
}

// Create the context with default values
const IndexContext = createContext<ResponseContextType | undefined>(undefined);

// Create a provider component
export const WeightProvider: React.FC<{ children: ReactNode }> = ({children}) => {
    const [weightDict, setWeight] = useState<WeightRecord>({});

    return (
        <IndexContext.Provider value={{weightDict, setWeight}}>
            {children}
        </IndexContext.Provider>
    );
};

// Create a custom hook for easy access to the context
export const useWeight = () => {
    const context = useContext(IndexContext);
    if (!context) {
        throw new Error('useResponse must be used within a DateProvider');
    }
    return context;
};
