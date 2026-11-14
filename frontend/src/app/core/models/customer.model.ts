export interface CustomerRequest {
  name: string;
  email: string;
  phone: string;
  cpf: string;
  address: string;
}

export interface CustomerResponse {
  id: number;
  name: string;
  email: string;
  phone: string;
  cpf: string;
  address: string;
  active: boolean;
}
