import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { CustomerService } from '../../../core/services/customer.service';
import { CustomerRequest } from '../../../core/models/customer.model';
import { AppHeaderComponent } from '../../../shared/components/organisms/app-header/app-header.component';


@Component({
  selector: 'app-customer-form',
  standalone: true,
  imports: [CommonModule, FormsModule, AppHeaderComponent],
  templateUrl: './customer-form.component.html',
  styleUrl: './customer-form.component.scss'
})
export class CustomerFormComponent implements OnInit {

  customer: CustomerRequest = {
    name: '', email: '', phone: '', cpf: '', address: ''
  };

  customerId: number | null = null;
  loading = false;
  errorMessage = '';

  constructor(
    private customerService: CustomerService,
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.customerId = +id;
      this.customerService.findById(this.customerId).subscribe({
        next: (data) => {
          this.customer = {
            name: data.name,
            email: data.email,
            phone: data.phone,
            cpf: data.cpf,
            address: data.address
          };
        }
      });
    }
  }

  get isEditing(): boolean {
    return this.customerId !== null;
  }

  onSubmit(): void {
    this.loading = true;
    this.errorMessage = '';

    const request = this.isEditing
      ? this.customerService.update(this.customerId!, this.customer)
      : this.customerService.create(this.customer);

    request.subscribe({
      next: () => this.router.navigate(['/customers']),
      error: () => {
        this.errorMessage = 'Erro ao salvar cliente.';
        this.loading = false;
      }
    });
  }

  cancel(): void {
    this.router.navigate(['/customers']);
  }
}
