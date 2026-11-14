import { CustomerResponse } from './customer.model';
import { ArtworkResponse, FrameResponse, GlassResponse } from './art-order.model';

export interface AttendanceState {
  // Passo 1 — Cliente
  customer: CustomerResponse | null;
  isNewCustomer: boolean;

  // Passo 2 — Produto
  artworkId: number | null;
  frameId: number | null;
  glassId: number | null;
  artworkHeightCm: number | null;
  artworkWidthCm: number | null;
  deliveryDate: string;
  discountAmount: number | null;

  // Passo 3 — Faturamento
  paymentType: string;
  paymentAmount: number | null;
  paymentNotes: string;
}
