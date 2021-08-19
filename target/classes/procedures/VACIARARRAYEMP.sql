create procedure vaciarArrayEmp is
    totVent   number;
    cursor empledo_cursor is select *
                  from empleado;
    ced       number;
begin
    for emp in empledo_cursor
        loop
            select SUM((deref(ventas.MIPROD).PRECIO_UNITARIO * ventas.NRO_UNIDADES)) into totVent from EMPLEADO e,table ( e.VENTAS ) ventas where e.cc = emp.CC;
            select count(*) into ced from HISTORICOVENTAS where CC = emp.CC;

            if totVent is null then
                totVent := 0;
            end if;
            if ced != 0 then
                update HISTORICOVENTAS set TOTALACUMULADOVENTAS = TOTALACUMULADOVENTAS + totVent where CC = emp.CC;
            else

                insert into HISTORICOVENTAS values (emp.CC, totVent);
            end if;
        end loop;
    update empleado set VENTAS = vent_varray();
    commit;
end;