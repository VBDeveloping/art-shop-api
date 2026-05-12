export type ArtSource = 'STORE_INVENTORY' | 'CUSTOMER_OWNED' | 'PRINT';

export interface ArtworkResponse {
  id: number;
  title: string;
  artist: string;
  description: string;
  price: number;
  stockQuantity: number;
  imageUrl: string;
  source: ArtSource;
}
