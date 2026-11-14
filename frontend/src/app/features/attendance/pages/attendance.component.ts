import { Component, OnInit } from '@angular/core';
import { CommonModule, CurrencyPipe } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { CustomerService } from '../../../core/services/customer.service';
import { ArtworkService } from '../../../core/services/artwork.service';
import { FrameService } from '../../../core/services/frame.service';
import { GlassService } from '../../../core/services/glass.service';
import { ArtOrderService } from '../../../core/services/art-order.service';
import { UserContextService } from '../../../core/services/user-context.service';
import { CustomerResponse, CustomerRequest } from '../../../core/models/customer.model';
import { ArtworkResponse, FrameResponse, GlassResponse } from '../../../core/models/art-order.model';
import { AttendanceState } from '../../../core/models/attendance.model';
import { AppHeaderComponent } from '../../../shared/components/organisms/app-header/app-header.component';


@Component({
  selector: 'app-attendance',
  standalone: true,
  imports: [CommonModule, FormsModule, CurrencyPipe, AppHeaderComponent],
  templateUrl: './attendance.component.html',
  styleUrl: './attendance.component.scss'
})
export class AttendanceComponent implements OnInit {

  currentStep = 1;
  totalSteps = 3;
  loading = false;
  errorMessage = '';

  customers: CustomerResponse[] = [];
  artworks: ArtworkResponse[] = [];
  frames: FrameResponse[] = [];
  glasses: GlassResponse[] = [];

  state: AttendanceState = {
    customer: null,
    isNewCustomer: false,
    artworkId: null,
    frameId: null,
    glassId: null,
    artworkHeightCm: null,
    artworkWidthCm: null,
    deliveryDate: '',
    discountAmount: null,
    paymentType: 'PIX',
    paymentAmount: null,
    paymentNotes: ''
  };

  newCustomer: CustomerRequest = {
    name: '', email: '', phone: '', cpf: '', address: ''
  };

  customerSearch = '';

  constructor(
    private customerService: CustomerService,
    private artworkService: ArtworkService,
    private frameService: FrameService,
    private glassService: GlassService,
    private artOrderService: ArtOrderService,
    public userContext: UserContextService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.customerService.findAll().subscribe(d => this.customers = d);
    this.artworkService.findAll().subscribe(d => this.artworks = d);
    this.frameService.findAll().subscribe(d => this.frames = d);
    this.glassService.findAll().subscribe(d => this.glasses = d);
  }

  get filteredCustomers(): CustomerResponse[] {
    if (!this.customerSearch) return this.customers;
    const q = this.customerSearch.toLowerCase();
    return this.customers.filter(c =>
      c.name.toLowerCase().includes(q) ||
      c.cpf.includes(q) ||
      c.email.toLowerCase().includes(q)
    );
  }

  get selectedArtwork(): ArtworkResponse | undefined {
    return this.artworks.find(a => a.id === Number(this.state.artworkId));
  }

  get selectedFrame(): FrameResponse | undefined {
    return this.frames.find(f => f.id === Number(this.state.frameId));
  }

  get selectedGlass(): GlassResponse | undefined {
    return this.glasses.find(g => g.id === Number(this.state.glassId));
  }

  get estimatedTotal(): number {
    let total = 0;

    // Valor da arte
    if (this.selectedArtwork) {
      total += Number(this.selectedArtwork.price) || 0;
    }

    // Valor da moldura por perímetro
    if (this.selectedFrame && this.state.artworkHeightCm && this.state.artworkWidthCm) {
      const h = Number(this.state.artworkHeightCm);
      const w = Number(this.state.artworkWidthCm);
      const perimeterMeters = ((h + w) * 2) / 100;
      const pricePerMeter = Number(this.selectedFrame.pricePerMeter) || 0;
      total += pricePerMeter * perimeterMeters;
    }

    // Valor do vidro por área
    if (this.selectedGlass && this.state.artworkHeightCm && this.state.artworkWidthCm) {
      const h = Number(this.state.artworkHeightCm);
      const w = Number(this.state.artworkWidthCm);
      const areaM2 = (h * w) / 10000;
      const pricePerM2 = Number(this.selectedGlass.pricePerSquareMeter) || 0;
      total += pricePerM2 * areaM2;
    }

    // Desconto
    if (this.state.discountAmount) {
      total -= Number(this.state.discountAmount);
    }

    return Math.max(0, total);
  }

  selectCustomer(customer: CustomerResponse): void {
    this.state.customer = customer;
  }

  nextStep(): void {
    if (this.currentStep < this.totalSteps) this.currentStep++;
  }

  prevStep(): void {
    if (this.currentStep > 1) this.currentStep--;
  }

  canProceedStep1(): boolean {
    if (this.state.isNewCustomer) {
      return !!this.newCustomer.name && !!this.newCustomer.cpf;
    }
    return this.state.customer !== null;
  }

  canProceedStep2(): boolean {
    return !!this.state.artworkId &&
      !!this.state.frameId &&
      !!this.state.artworkHeightCm &&
      !!this.state.artworkWidthCm &&
      !!this.state.deliveryDate;
  }

  finish(): void {
    this.loading = true;
    this.errorMessage = '';

    const createOrder = (customerId: number) => {
      this.artOrderService.create({
        artworkId: Number(this.state.artworkId!),
        frameId: Number(this.state.frameId!),
        glassId: this.state.glassId ? Number(this.state.glassId) : undefined,
        sellerId: this.userContext.getCurrentUser()!.id,
        customerId,
        artworkHeightCm: Number(this.state.artworkHeightCm!),
        artworkWidthCm: Number(this.state.artworkWidthCm!),
        deliveryDate: this.state.deliveryDate,
        discountAmount: this.state.discountAmount ? Number(this.state.discountAmount) : undefined
      }).subscribe({
        next: () => this.router.navigate(['/dashboard']),
        error: () => {
          this.errorMessage = 'Erro ao criar pedido.';
          this.loading = false;
        }
      });
    };

    if (this.state.isNewCustomer) {
      this.customerService.create(this.newCustomer).subscribe({
        next: (customer) => createOrder(customer.id),
        error: () => {
          this.errorMessage = 'Erro ao cadastrar cliente.';
          this.loading = false;
        }
      });
    } else {
      createOrder(this.state.customer!.id);
    }
  }

  confirmCancel(): void {
    const confirmed = confirm('Deseja cancelar o atendimento? Os dados serão perdidos.');
    if (confirmed) this.router.navigate(['/dashboard']);
  }
}
