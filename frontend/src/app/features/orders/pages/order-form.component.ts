import { Component, OnInit } from '@angular/core';
import { CommonModule, CurrencyPipe } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { ArtOrderService } from '../../../core/services/art-order.service';
import { ArtworkService } from '../../../core/services/artwork.service';
import { FrameService } from '../../../core/services/frame.service';
import { GlassService } from '../../../core/services/glass.service';
import { ArtworkResponse, FrameResponse, GlassResponse, ArtOrderRequest } from '../../../core/models/art-order.model';

@Component({
  selector: 'app-order-form',
  standalone: true,
  imports: [CommonModule, FormsModule, CurrencyPipe],
  templateUrl: './order-form.component.html',
  styleUrl: './order-form.component.scss'
})
export class OrderFormComponent implements OnInit {

  artworks: ArtworkResponse[] = [];
  frames: FrameResponse[] = [];
  glasses: GlassResponse[] = [];

  order: ArtOrderRequest = {
    artworkId: 0,
    frameId: 0,
    glassId: undefined,
    sellerId: 0,
    artworkHeightCm: 0,
    artworkWidthCm: 0,
    discountAmount: undefined
  };

  loading = false;
  errorMessage = '';

  constructor(
    private artOrderService: ArtOrderService,
    private artworkService: ArtworkService,
    private frameService: FrameService,
    private glassService: GlassService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.artworkService.findAll().subscribe(data => this.artworks = data);
    this.frameService.findAll().subscribe(data => this.frames = data);
    this.glassService.findAll().subscribe(data => this.glasses = data);
  }

  onSubmit(): void {
    this.loading = true;
    this.errorMessage = '';

    this.artOrderService.create(this.order).subscribe({
      next: () => {
        this.router.navigate(['/orders']);
      },
      error: () => {
        this.errorMessage = 'Erro ao criar pedido. Verifique os dados.';
        this.loading = false;
      }
    });
  }

  cancel(): void {
    this.router.navigate(['/orders']);
  }
}
