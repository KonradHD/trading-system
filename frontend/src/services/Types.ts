export type gender = "male" | "female" | "other";

export type UserData = {
    name: string;
    surname: string;
    email: string,
    gender: gender;
    dateOfBirth: string;
    city: string;
    address: string,
    phone: string,
    pesel: string,
};

export type RegistrationProfile = {
    email: string,
    name: string;
    surname: string;
    gender: gender;
    dateOfBirth: string;
    city: string;
    address: string,
    phone: string,
    pesel: string,
}

export type RegistrationUser = {
    login: string,
    password: string,
    profile: RegistrationProfile,
}