import {FC} from "react";
import HeaderPage from "./HeaderPage";
import '../styles/qr.css'
import {useNavigate} from "react-router-dom";
import {QrReader} from 'react-qr-reader';

const QRScanPage: FC<{ setMenuVisible: (visible: boolean) => void }> = ({setMenuVisible}) => {
    const navigate = useNavigate();

    const reNavigate = (res: string) => {
        /* here making fetch to backend with login and qr data, then getting updated list and
        * sending it into the grocery temporary*/
        sessionStorage.setItem('qrValue', res.toString());
        return;
    }

    const reDirect = () => {
        navigate('/grocery/*', {replace: true});
        window.location.reload()
    }


    return (
        <>
            <HeaderPage setMenuVisible={setMenuVisible}/>
            <div className='qr-page'>
                <div className="qr-heading">
                    <div onClick={reDirect}>
                        <svg width="20" height="17" viewBox="0 0 14 11" fill="none" xmlns="http://www.w3.org/2000/svg">
                            <path
                                d="M6.20676 10.6948C6.01923 10.8822 5.76492 10.9875 5.49976 10.9875C5.23459 10.9875 4.98028 10.8822 4.79276 10.6948L0.292756 6.19476C0.105285 6.00723 -3.05176e-05 5.75293 -3.05176e-05 5.48776C-3.05176e-05 5.2226 0.105285 4.96829 0.292756 4.78076L4.79276 0.280763C4.98136 0.0986042 5.23396 -0.00218964 5.49616 8.86917e-05C5.75835 0.00236702 6.00917 0.107536 6.19457 0.292944C6.37998 0.478352 6.48515 0.729165 6.48743 0.991362C6.48971 1.25356 6.38891 1.50616 6.20676 1.69476L3.49976 4.48776L12.9998 4.48776C13.265 4.48776 13.5193 4.59312 13.7069 4.78066C13.8944 4.96819 13.9998 5.22255 13.9998 5.48776C13.9998 5.75298 13.8944 6.00733 13.7069 6.19487C13.5193 6.38241 13.265 6.48776 12.9998 6.48776L3.49976 6.48776L6.20676 9.28076C6.39423 9.46829 6.49954 9.7226 6.49954 9.98776C6.49954 10.2529 6.39423 10.5072 6.20676 10.6948Z"
                                fill="#4D544B"/>
                        </svg>
                    </div>
                    <div> Scan the QR</div>
                </div>
                <div className="qr-reader">
                    <QrReader
                        onResult={(result, error) => {
                            if (!!result) {
                                reNavigate(result?.getText());
                                navigate('/grocery-temporary', {replace: true});
                                window.location.reload()
                            }
                        }}
                        constraints={{facingMode: 'environment'}}
                    />
                </div>
            </div>
        </>
    )
}

export default QRScanPage;