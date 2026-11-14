export interface LoginRequest {
  email: string;
  password: string;
}

export interface LoginResponse {
  token: string;
}

export interface TokenPayload {
  sub: string;
  iat: number;
  exp: number;
}
