export type gender = "male" | "female" | "other";

export type UserData = {
    name: string;
    surname: string;
    email: string,
    gender: gender;
    dateofbirth: string;
    city: string;
    address: string,
    phone: string,
    pesel: string,
};