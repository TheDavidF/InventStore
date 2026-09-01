package dev.fercho.inventstore.controller;


import dev.fercho.inventstore.dto.response.ReporteCajaResponse;
import dev.fercho.inventstore.dto.response.ReporteComprasResponse;
import dev.fercho.inventstore.dto.response.ReporteInventarioResponse;
import dev.fercho.inventstore.dto.response.ReporteVentaResponse;
import dev.fercho.inventstore.mapper.ReporteCajaMapper;
import dev.fercho.inventstore.mapper.ReporteCompraMapper;
import dev.fercho.inventstore.mapper.ReporteInventarioMapper;
import dev.fercho.inventstore.mapper.ReporteVentaMapper;
import dev.fercho.inventstore.service.ReporteService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/reportes")
public class ReporteController {

    private final ReporteService reporteService;
    private final ReporteVentaMapper reporteVentaMapper;
    private final ReporteCompraMapper reporteCompraMapper;
    private final ReporteInventarioMapper reporteInventarioMapper;
    private final ReporteCajaMapper reporteCajaMapper;
    public ReporteController(
            ReporteService reporteService,
            ReporteVentaMapper reporteVentaMapper
            ,ReporteCompraMapper reporteCompraMapper
            ,ReporteInventarioMapper reporteInventarioMapper
            ,ReporteCajaMapper reporteCajaMapper) {
        this.reporteService = reporteService;
        this.reporteVentaMapper = reporteVentaMapper;
        this.reporteCompraMapper = reporteCompraMapper;
        this.reporteInventarioMapper = reporteInventarioMapper;
        this.reporteCajaMapper = reporteCajaMapper;
    }

    @GetMapping("/ventas")
    public List<ReporteVentaResponse> obtenerReportesVentasPorFecha(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate desde
            ,@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate hasta){

        return reporteVentaMapper.toList(reporteService.obtenerVentas(desde,hasta));
    }

    @GetMapping("/compras")
    public List<ReporteComprasResponse> obtenerReportesComprasPorFecha(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate desde
            ,@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate hasta){

        return reporteCompraMapper.toList(reporteService.obtenerCompras(desde,hasta));
    }

    @GetMapping("/inventario")
    public List<ReporteInventarioResponse> obtenerReporteInventario(){
        return reporteInventarioMapper.toList(
                reporteService.obtenerInventario()
        );
    }


    @GetMapping("/cajas")
    public List<ReporteCajaResponse> obtenerReporteCaja(@RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate desde
            ,@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate hasta){
        return reporteCajaMapper.toList(reporteService.obtenerCajas(desde,hasta));

    }
}
