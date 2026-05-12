import { ArtSource } from './artwork.model';
import { UserRole } from './user.model';

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

export interface FrameResponse {
  id: number;
  code: string;
  name: string;
  material: string;
  color: string;
  faceWidthCm: number;
  pricePerMeter: number;
  stockMeters: number;
}

export interface GlassResponse {
  id: number;
  type: string;
  description: string;
  pricePerSquareMeter: number;
}

export interface UserResponse {
  id: number;
  name: string;
  email: string;
  role: UserRole;
  active: boolean;
}

export interface ArtOrderResponse {
  id: number;
  orderDate: string;
  artworkHeightCm: number;
  artworkWidthCm: number;
  artwork: ArtworkResponse;
  frame: FrameResponse;
  glass: GlassResponse;
  seller: UserResponse;
  discountAmount: number;
  totalValue: number;
}

export interface ArtOrderRequest {
  artworkId: number;
  frameId: number;
  glassId?: number;
  sellerId: number;
  artworkHeightCm: number;
  artworkWidthCm: number;
  discountAmount?: number;
}
