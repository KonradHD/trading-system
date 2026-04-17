import type { UserData } from "../../services/Types"
import '../../pages/account/RegisterPage.css';

type UserRegistrationProps = {
    data: UserData,
    onUpdate: <K extends keyof UserData>(field: K, value: UserData[K]) => void,
    emailError: boolean,
}

export default function RegistrationFormContact({ data, onUpdate, emailError }: UserRegistrationProps) {



    return (
        <div className="user-div">
            <h1>Contact data:</h1>
            {/* EMAIL */}
            <div className="form-group">
                <label>Email:</label>
                <input
                    type="email"
                    value={data.email}
                    onChange={(e) => onUpdate("email", e.target.value)}
                    className="std-input"
                    required
                />
                {
                    emailError &&
                    <span className="error-message">Given email already exists.</span>
                }
            </div>
            <div className="form-group">
                <label>Phone:</label>
                <input
                    type="text"
                    inputMode="numeric"
                    maxLength={9}
                    value={data.phone}
                    onChange={(e) => {
                        const val = e.target.value;

                        if (/^\d*$/.test(val)) {
                            onUpdate("phone", val);
                        }
                    }}
                    className="std-input"
                    required
                />
            </div>

            <div className="form-row">
                <div className="form-group">
                    <label>City:</label>
                    <input
                        type="text"
                        value={data.city}
                        onChange={(e) => onUpdate("city", e.target.value)}
                        className="std-input"
                        required
                    />
                </div>

                <div className="form-group">
                    <label>Adress:</label>
                    <input
                        type="text"
                        value={data.address}
                        onChange={(e) => onUpdate("address", e.target.value)}
                        className="std-input"
                        required
                    />
                </div>

            </div>

        </div>
    )
}