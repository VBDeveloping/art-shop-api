export interface LoginRequest {
  email: string;
  password: string;
}

export interface LoginResponse {
  token: string;
}

export type UserRole = 'ADMIN' | 'SALES_USER';
