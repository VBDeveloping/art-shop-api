import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { UserManagementService } from '../../../core/services/user-management.service';
import { UserResponse, UserRequest, UserRole } from '../../../core/models/user.model';

@Component({
  selector: 'app-user-list',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './user-list.component.html',
  styleUrl: './user-list.component.scss'
})
export class UserListComponent implements OnInit {

  users: UserResponse[] = [];
  loading = true;
  errorMessage = '';
  showForm = false;

  newUser: UserRequest = {
    name: '', email: '', password: '', role: 'SELLER'
  };

  roles: UserRole[] = ['ADMIN', 'SELLER', 'STOCK', 'FINANCIAL'];

  roleLabels: Record<UserRole, string> = {
    ADMIN: 'Administrador',
    SELLER: 'Vendedor',
    STOCK: 'Estoque',
    FINANCIAL: 'Financeiro'
  };

  constructor(
    private userService: UserManagementService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.load();
  }

  load(): void {
    this.userService.findAll().subscribe({
      next: (data) => {
        this.users = data;
        this.loading = false;
      },
      error: () => {
        this.errorMessage = 'Erro ao carregar usuários.';
        this.loading = false;
      }
    });
  }

  createUser(): void {
    this.userService.create(this.newUser).subscribe({
      next: () => {
        this.showForm = false;
        this.newUser = { name: '', email: '', password: '', role: 'SELLER' };
        this.load();
      },
      error: () => {
        this.errorMessage = 'Erro ao criar usuário.';
      }
    });
  }

  toggleStatus(id: number): void {
    this.userService.toggleStatus(id).subscribe({
      next: () => this.load()
    });
  }

  back(): void {
    this.router.navigate(['/dashboard']);
  }
}
