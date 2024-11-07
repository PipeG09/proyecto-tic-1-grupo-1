// src/pages/Registro.js
import React from 'react';
import { Formik, Form, Field, ErrorMessage } from 'formik';
import * as Yup from 'yup';
import axios from 'axios';
import { useHistory } from 'react-router-dom';

function Registro() {
    const history = useHistory();

    const initialValues = {
        fullname: '',
        username: '',
        password: '',
    };

    const validationSchema = Yup.object({
        fullname: Yup.string()
            .required('El nombre completo es obligatorio'),
        username: Yup.string()
            .required('El nombre de usuario es obligatorio'),
        password: Yup.string()
            .min(6, 'La contraseña debe tener al menos 6 caracteres')
            .required('La contraseña es obligatoria'),
    });

    const onSubmit = (values, { setSubmitting, setFieldError }) => {
        axios
            .post('/api/user/register', {
                fullname: values.fullname,
                username: values.username,
                password: values.password,
            })
            .then((response) => {
                // Registro exitoso
                alert('Registro exitoso. Por favor, inicia sesión.');
                history.push('/login');
            })
            .catch((error) => {
                setSubmitting(false);
                if (error.response) {
                    // El backend respondió con un código de estado distinto de 2xx
                    if (error.response.status === 400) {
                        setFieldError('username', 'El nombre de usuario ya existe');
                    } else {
                        alert('Error al registrar. Por favor, inténtalo de nuevo.');
                    }
                } else {
                    // El backend no respondió o ocurrió un error al configurar la solicitud
                    alert('Error de conexión. Por favor, inténtalo de nuevo más tarde.');
                }
            });
    };

    return (
        <div className="container mt-4">
            <h2>Registro</h2>
            <Formik
                initialValues={initialValues}
                validationSchema={validationSchema}
                onSubmit={onSubmit}
            >
                {({ isSubmitting }) => (
                    <Form>
                        <div className="form-group">
                            <label htmlFor="fullname">Nombre Completo</label>
                            <Field
                                type="text"
                                id="fullname"
                                name="fullname"
                                className="form-control"
                            />
                            <ErrorMessage
                                name="fullname"
                                component="div"
                                className="text-danger"
                            />
                        </div>
                        <div className="form-group">
                            <label htmlFor="username">Nombre de Usuario</label>
                            <Field
                                type="text"
                                id="username"
                                name="username"
                                className="form-control"
                            />
                            <ErrorMessage
                                name="username"
                                component="div"
                                className="text-danger"
                            />
                        </div>
                        <div className="form-group">
                            <label htmlFor="password">Contraseña</label>
                            <Field
                                type="password"
                                id="password"
                                name="password"
                                className="form-control"
                            />
                            <ErrorMessage
                                name="password"
                                component="div"
                                className="text-danger"
                            />
                        </div>
                        <button
                            type="submit"
                            className="btn btn-primary"
                            disabled={isSubmitting}
                        >
                            Registrarse
                        </button>
                    </Form>
                )}
            </Formik>
        </div>
    );
}

export default Registro;
