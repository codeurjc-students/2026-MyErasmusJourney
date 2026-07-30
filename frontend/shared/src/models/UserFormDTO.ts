export interface UserFormDTO{
    fullName:String;
    displayName: String;
    email: String;
    city: String|null;
    country: String|null;
    password: String;
    passwordConfirmation: String;
}