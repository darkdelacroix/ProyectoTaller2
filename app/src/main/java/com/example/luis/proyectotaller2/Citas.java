package com.example.luis.proyectotaller2;

public class Citas {
    private String nombreTaller,matriculaVehiculo, fecha, hora,id;
    private int kilometros;

    public Citas(String nombreTaller, String matriculaVehiculo, String fecha, String hora, String id, int kilometros) {
        this.nombreTaller = nombreTaller;
        this.matriculaVehiculo = matriculaVehiculo;
        this.fecha = fecha;
        this.hora = hora;
        this.id = id;//imagen id
        this.kilometros = kilometros;
    }

    public String getNombreTaller() {
        return nombreTaller;
    }

    public void setNombreTaller(String nombreTaller) {
        this.nombreTaller = nombreTaller;
    }

    public String getMatriculaVehiculo() {
        return matriculaVehiculo;
    }

    public void setMatriculaVehiculo(String matriculaVehiculo) {
        this.matriculaVehiculo = matriculaVehiculo;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getKilometros() {
        return kilometros;
    }

    public void setKilometros(int kilometros) {
        this.kilometros = kilometros;
    }
}
