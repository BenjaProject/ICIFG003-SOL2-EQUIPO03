import { Component, inject, signal } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ContactoService } from '../../services/contacto.service';

@Component({
  selector: 'app-contacto',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './contacto.html',
  styleUrl: './contacto.css',
})
export class Contacto {
  private readonly contactoService = inject(ContactoService);

  readonly nombre = signal<string>('');
  readonly correo = signal<string>('');
  readonly mensaje = signal<string>('');
  
  readonly errors = signal<{ nombre?: string; correo?: string; mensaje?: string }>({});
  readonly submitted = signal<boolean>(false);
  readonly sending = signal<boolean>(false);
  readonly successMessage = signal<string | null>(null);
  readonly errorMessage = signal<string | null>(null);

  onSubmit(event: Event): void {
    event.preventDefault();
    this.submitted.set(true);
    this.successMessage.set(null);
    this.errorMessage.set(null);

    const validationErrors: { nombre?: string; correo?: string; mensaje?: string } = {};

    // Nombre validation
    if (!this.nombre().trim()) {
      validationErrors.nombre = 'El nombre es obligatorio.';
    }

    // Correo validation
    const emailValue = this.correo().trim();
    if (!emailValue) {
      validationErrors.correo = 'El correo electrónico es obligatorio.';
    } else {
      const emailRegex = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
      if (!emailRegex.test(emailValue)) {
        validationErrors.correo = 'Por favor, ingresa un correo electrónico válido.';
      }
    }

    // Mensaje validation
    const mensajeValue = this.mensaje().trim();
    if (!mensajeValue) {
      validationErrors.mensaje = 'El mensaje es obligatorio.';
    } else if (mensajeValue.length < 20) {
      validationErrors.mensaje = `El mensaje debe tener al menos 20 caracteres (actualmente tiene ${mensajeValue.length}).`;
    }

    this.errors.set(validationErrors);

    // If there are no errors, proceed with submit
    if (Object.keys(validationErrors).length === 0) {
      this.sending.set(true);
      const data = {
        nombre: this.nombre().trim(),
        correo: this.correo().trim(),
        mensaje: this.mensaje().trim()
      };

      this.contactoService.enviarMensaje(data).subscribe({
        next: () => {
          this.successMessage.set('¡Tu mensaje ha sido enviado con éxito! Nos pondremos en contacto contigo pronto.');
          this.nombre.set('');
          this.correo.set('');
          this.mensaje.set('');
          this.submitted.set(false);
          this.errors.set({});
          this.sending.set(false);
        },
        error: (err) => {
          console.error(err);
          this.errorMessage.set('Ocurrió un error al enviar el mensaje. Por favor, de nuevo más tarde.');
          this.sending.set(false);
        }
      });
    }
  }
}
