import Swal from 'sweetalert2';
import withReactContent from 'sweetalert2-react-content';

/**
 * icon : success, error, warning, info, question
 */

const MySwal = withReactContent(Swal)

// 기본 alert 디자인
export const alert = (title, text, icon, callback) => {
    MySwal.fire({
        title: title,
        text: text,
        icon: icon,
    })
    .then( callback )
  }

// confirm 확인창 디자인
export const confirm = (title, text, icon, callback) => {
    MySwal.fire({
        title: title,
        text: text,
        icon: icon,
        showCancelButton: true,
        cancelButtonColor: "#d33",
        cancelButtonText: "No",
        confirmButtonColor: "#3085d6",
        confirmButtonText: "Yes",
    })
    .then( callback )
}