// src/pages/Login.js
import React, { useContext } from 'react';
import { Formik, Form, Field, ErrorMessage } from 'formik';
import * as Yup from 'yup';
import axios from 'axios';
import { useNavigate, Link } from 'react-router-dom';
import { AuthContext } from '../context/AuthContext';

function Login() {
    const navigate = useNavigate();
    const { iniciarSesion } = useContext(AuthContext);

    const initialValues = {
        username: '',
        password: '',
    };

    const validationSchema = Yup.object({
        username: Yup.string().required('El nombre de usuario es obligatorio'),
        password: Yup.string().required('La contraseña es obligatoria'),
    });

    const onSubmit = (values, { setSubmitting, setFieldError }) => {
        axios
            .post('/api/user/login', {
                username: values.username,
                password: values.password,
            })
            .then((response) => {
                // Inicio de sesión exitoso
                iniciarSesion(response.data); // Actualiza el contexto de autenticación
                navigate('/'); // Redirige a la página principal
            })
            .catch((error) => {
                setSubmitting(false);
                if (error.response) {
                    // El backend respondió con un código de estado distinto de 2xx
                    if (error.response.status === 400) {
                        setFieldError('password', 'Contraseña incorrecta');
                    } else if (error.response.status === 404) {
                        setFieldError('username', 'El usuario no existe');
                    } else {
                        alert('Error al iniciar sesión. Por favor, inténtalo de nuevo.');
                    }
                } else {
                    // El backend no respondió o ocurrió un error al configurar la solicitud
                    alert('Error de conexión. Por favor, inténtalo de nuevo más tarde.');
                }
            });
    };

    return (
        <div className="container mt-4">
            <h2>Iniciar Sesión</h2>
            <Formik
                initialValues={initialValues}
                validationSchema={validationSchema}
                onSubmit={onSubmit}
            >
                {({ isSubmitting }) => (
                    <Form>
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
                            Iniciar Sesión
                        </button>
                    </Form>
                )}
            </Formik>
            <p className="mt-3">
                ¿No tienes una cuenta? <Link to="/registro">Regístrate aquí</Link>
            </p>
        </div>
    );
}

export default Login;
