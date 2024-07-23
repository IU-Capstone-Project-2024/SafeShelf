export interface QRReaderProps {
    onResult: (result: QrReaderResult | null, error: Error | null) => void;
    onError?: (error: Error | null) => void;
    constraints?: MediaTrackConstraints;
    delay: number;
    style?: React.CSSProperties;
}

export interface QrReaderResult {
    getText(): string;
}