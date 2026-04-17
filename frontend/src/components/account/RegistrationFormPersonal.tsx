import type { gender, UserData } from "../../services/Types"
import '../../pages/account/RegisterPage.css';

type UserRegistrationProps = {
    data: UserData,
    onUpdate: <K extends keyof UserData>(field: K, value: UserData[K]) => void,
    peselError: boolean,
}

export default function RegistrationFormPersonal({ data, onUpdate, peselError }: UserRegistrationProps) {


    function setGender(genderStr: string) {
        if (genderStr === "male") {
            const gender: gender = "male";
            onUpdate("gender", gender);
        }
        if (genderStr === "female") {
            const gender: gender = "female";
            onUpdate("gender", gender);
        }
        if (genderStr === "other") {
            const gender: gender = "other";
            onUpdate("gender", gender);
        }
    }



    return (
        <div className="user-div">
            <h1>Personal data:</h1>
            <div className="form-group">
                <label>Name:</label>
                <input
                    type="text"
                    value={data.name}
                    onChange={(e) => onUpdate("name", e.target.value)}
                    className="std-input"
                    required
                />
            </div>
            <div className="form-group">
                <label>Surname:</label>
                <input
                    type="text"
                    value={data.surname}
                    onChange={(e) => onUpdate("surname", e.target.value)}
                    className="std-input"
                    required
                />
            </div>

            <div className="form-row">
                <div className="form-group half">
                    <label>Gender:</label>
                    <select
                        value={data.gender}
                        onChange={(e) => setGender(e.target.value)}
                        className="std-input"
                    >
                        <option value="female">Female</option>
                        <option value="male">Male</option>
                        <option value="other">Other</option>
                    </select>
                </div>

            </div>
            <div className="form-group">
                <label>Date of birth:</label>
                <input
                    type="date"
                    value={data.dateofbirth}
                    onChange={(e) => onUpdate("dateofbirth", e.target.value)}
                    className="std-input"
                    required
                />
            </div>

            <div className="form-group pesel-div">
                <label>Pesel:</label>
                <input
                    type="text"
                    inputMode="numeric"
                    maxLength={11}
                    value={data.pesel}
                    onChange={(e) => {
                        const val = e.target.value;
                        if (/^\d*$/.test(val)) {
                            onUpdate("pesel", val);
                        }
                    }}
                    className="std-input"
                    required
                />
                {
                    peselError &&
                    <span className="error-message">Given pesel already exists.</span>
                }
            </div>

        </div>
    )
}