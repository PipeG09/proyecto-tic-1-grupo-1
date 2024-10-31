// src/pages/Registro.js
import React from 'react';
import { Formik, Form, Field, ErrorMessage } from 'formik';
import * as Yup from 'yup';

function Registro() {
    const initialValues = {
        nombre: '',
        email: '',
        contraseña: '',
    };

    const validationSchema = Yup.object({
        nombre: Yup.string().required('Requerido'),
        email: Yup.string().email('Email inválido').required('Requerido'),
        contraseña: Yup.string().min(6, 'Debe tener al menos 6 caracteres').required('Requerido'),
    });

    const onSubmit = (values) => {
        // Aquí llamas a tu API para registrar al usuario
        console.log(values);
    };

    return (
        <div>
            <h2>Registro</h2>
            <Formik initialValues={initialValues} validationSchema={validationSchema} onSubmit={onSubmit}>
                <Form>
                    <div>
                        <label>Nombre:</label>
                        <Field name="nombre" type="text" />
                        <ErrorMessage name="nombre" />
                    </div>
                    <div>
                        <label>Email:</label>
                        <Field name="email" type="email" />
                        <ErrorMessage name="email" />
                    </div>
                    <div>
                        <label>Contraseña:</label>
                        <Field name="contraseña" type="password" />
                        <ErrorMessage name="contraseña" />
                    </div>
                    <button type="submit">Registrarse</button>
                </Form>
            </Formik>
        </div>
    );
}

export default Registro;
