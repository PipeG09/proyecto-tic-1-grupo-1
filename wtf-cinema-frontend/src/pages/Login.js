// src/pages/Login.js
import React from 'react';
import { Formik, Form, Field, ErrorMessage } from 'formik';
import * as Yup from 'yup';

function Login() {
    const initialValues = {
        email: '',
        contraseña: '',
    };

    const validationSchema = Yup.object({
        email: Yup.string().email('Email inválido').required('Requerido'),
        contraseña: Yup.string().required('Requerido'),
    });

    const onSubmit = (values) => {
        // Llamada a la API para iniciar sesión
        console.log(values);
    };

    return (
        <div>
            <h2>Iniciar Sesión</h2>
            <Formik initialValues={initialValues} validationSchema={validationSchema} onSubmit={onSubmit}>
                <Form>
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
                    <button type="submit">Ingresar</button>
                </Form>
            </Formik>
        </div>
    );
}

export default Login;
