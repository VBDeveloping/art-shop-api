export type UserRole = 'ADMIN' | 'SELLER' | 'STOCK' | 'FINANCIAL';

export interface UserRequest {
  name: string;
  email: string;
  password: string;
  role: UserRole;
}

export interface UserResponse {
  id: number;
  name: string;
  email: string;
  role: UserRole;
  active: boolean;
}
