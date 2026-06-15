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

export type Profile = {
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

export type DisplayableProfile = {
    email: string,
    name: string;
    surname: string;
    gender: gender;
    dateOfBirth: string;
    city: string;
    address: string,
    phone: string
}

export type RegistrationUser = {
    login: string,
    password: string,
    profile: Profile,
}