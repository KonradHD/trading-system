import { useState, useEffect } from "react"
import RegistrationFormPersonal from "../../components/account/RegistrationFormPersonal";
import { PASSWORD_RULES } from '../../services/validationRules'
import { type UserData } from "../../services/Types";
import CredentialsForm from "../../components/account/CredentialsForm";
import { useNavigate } from "react-router-dom";
import RegistrationFormContact from "../../components/account/RegistrationFormContact";
import './RegisterPage.css'
import API_URL from "../../services/api";
// import type { TokenPayload } from "../components/Types";
// import { getUserFromToken } from "../validationRules";
// import { handleLogout } from "../services/api";

export default function RegisterPage() {
    const navigate = useNavigate();
    const [step, setStep] = useState(1);
    const [loginError, setLoginError] = useState(false);
    const [userEmailError, setUserEmailError] = useState(false);
    const [peselError, setPeselError] = useState(false);
    // const [userInfo, setUserInfo] = useState<TokenPayload | null>(getUserFromToken());

    // useEffect(() => {
    //     if (userInfo) {
    //         handleLogout();
    //     }
    // }, [])


    const [userData, setUserData] = useState<UserData>({
        name: "",
        surname: "",
        email: "",
        gender: "male",
        dateofbirth: "",
        city: "",
        address: "",
        phone: "",
        pesel: ""
    });

    const [validDataStatus, setValidDataStatus] = useState(false);
    const [login, setLogin] = useState("");
    const [password, setPassword] = useState("");
    const [confirmPassword, setConfirmPassword] = useState("");
    const [formError, setFormError] = useState<string | null>(null);
    const [loading, setLoading] = useState(false);


    function updateUserData<K extends keyof UserData>(field: K, value: UserData[K]) {
        setUserData(prev => ({
            ...prev,
            [field]: value
        }))
    }

    async function handleRegistration(e: React.FormEvent) {
        e.preventDefault(); // zapobiega odświeżeniu strony
        setFormError(null);
        setLoading(true);
        console.log("handleRegistration")
        if (!validDataStatus) {
            setFormError("Invalid data.");
            setLoading(false);
            return;
        }

        try {
            console.log("Saving registration data.");
        
            const data = {
                login: login,
                password: password,
                role: "user",
                name: userData.name,
                surname: userData.surname,
                gender: userData.gender,
                dateOfBirth: userData.dateofbirth,
                city: userData.city,
                address: userData.address,
                phone: userData.phone,
                pesel: userData.pesel,
                email: userData.email,
            }
            


            const response = await fetch(`${API_URL}/register-user`, {
                method: "POST",
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({ data }),
            });

            if (!response.ok) {
                const responseData = await response.json();
                console.log(responseData)
                if (responseData.constraint === 'users_login_unique') {
                    setLoginError(true);
                }
                if (responseData.constraint === 'patients_email_unique') {
                    setUserEmailError(true);
                    setStep(2);
                }
                if (responseData.constraint === "pesel_unique") {
                    setPeselError(true);
                    setStep(1);
                }

                throw new Error("Registration error.");
            }

            const responseData = await response.json();
            if (responseData.success) {
                alert(`Approved registration!`);
                navigate(`/login`);
            } else {
                alert('Registration unsucceeded.');
            }

        } catch (err) {
            console.log(err)
        } finally {
            setLoading(false);
        }
    }


    function changeStep(up: boolean) {
        if (!up) {
            setStep(prevStep => prevStep - 1);
            setFormError(null);
            return;
        }

        if (checkDataFilled() && up) {
            setStep(prevStep => prevStep + 1);
            setFormError(null);
        } else {
            setFormError("Check the data.");
        }
    }

    function checkDataFilled() {
        let checkData: string[] = [];
        const currentData : any = userData;

        if (step === 1) {
            checkData = ["name", "surname", "gender", "dateofbirth", "pesel"];
        }
        if (step === 2) {
            checkData = ["email", "phone", "city", "address"];
        }
        

        return checkData.every(key => {
            const value = currentData[key];

            if (Array.isArray(value)) {
                if (key === "prices" && value.filter(val => val === 0).length > 0) {
                    return false;
                }
                return value.length > 0;
            }

            return value && value.toString().trim().length > 0;
        });
    }

    useEffect(() => {

        function validatePassword() {
            const doPasswordsMatch = password === confirmPassword && password.length > 0;
            const isPasswordRuleValid = PASSWORD_RULES.every(r => r.rule(password));
            const passwordValid = doPasswordsMatch && isPasswordRuleValid;

            if (passwordValid) {
                setValidDataStatus(true);
                return;
            }
            setValidDataStatus(false);
        }

        validatePassword();

    }, [password, confirmPassword])

    return (
        <section className="signing-up">
            <button
                onClick={() => navigate(-1)}
                style={{
                    alignSelf: 'flex-start',
                    marginBottom: '1rem',
                    background: 'none',
                    border: 'none',
                    color: '#64748b',
                    cursor: 'pointer',
                    fontSize: '1rem',
                    display: 'flex',
                    alignItems: 'center',
                    gap: '0.5rem'
                }}
            >
                &larr; Back
            </button>
            <h1>Sign up</h1>
            <form onSubmit={(e) => handleRegistration(e)}>
                {
                    step === 1 &&
                    <div className="personal-data">
                        <div className="personal-form">
                            <RegistrationFormPersonal
                                data={userData}
                                onUpdate={updateUserData}
                                peselError={peselError} /> 
                            {formError && (
                                <p style={{ color: "red", fontSize: "14px", marginBottom: "10px" }}>
                                    {formError}
                                </p>
                            )}


                        </div>

                    </div>

                }

                {
                    step === 2 &&
                    <div className="contact-form">
                        <RegistrationFormContact
                            data={userData}
                            onUpdate={updateUserData}
                            emailError={userEmailError} /> 
                        {formError && (
                            <p style={{ color: "red", fontSize: "14px", marginBottom: "10px" }}>
                                {formError}
                            </p>
                        )}


                    </div>
                }

                {
                    step === 3 &&
                    <div className="credentials-container">
                        <CredentialsForm
                            login={login}
                            setLogin={setLogin}
                            password={password}
                            setPassword={setPassword}
                            confirmPassword={confirmPassword}
                            setConfirmPassword={setConfirmPassword}
                            loginError={loginError} />
                        <button id="register-btn" type="submit" disabled={loading}>
                            {loading ? "Registering..." : "Register"}
                        </button>
                    </div>
                }

            </form>
            <div className="form-navigation">
                <button
                    id="prev-btn"
                    type="button"
                    onClick={() => changeStep(false)}
                    style={{ visibility: step > 1 ? 'visible' : 'hidden' }}
                >
                    Previous
                </button>

                {
                    step < 3 ?
                        <button id="next-btn" type="button" onClick={() => changeStep(true)}>Next</button>
                        : <div style={{ flex: 1 }}></div>
                }
            </div>
        </section>
    )
}